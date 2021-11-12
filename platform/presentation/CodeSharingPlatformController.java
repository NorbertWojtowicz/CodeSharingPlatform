package platform.presentation;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import platform.ApiCode;
import platform.businesslayer.CodeSnippet;
import platform.businesslayer.CodeSnippetService;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class CodeSharingPlatformController {
    private int codeSnippetsCounter = 0;
    List<ApiCode> codeSnippets = new ArrayList<>();

    @Autowired
    public CodeSnippetService codeSnippetService;

    @GetMapping("/code/new")
    public String generateNewCodeForm() {
        return "newCodeForm";
    }

    @PostMapping("/api/code/new")
    public ResponseEntity postNewCode(@RequestBody CodeSnippet code) {
        code.refreshDateTime();
        CodeSnippet codeSnippet = codeSnippetService.save(
                new CodeSnippet(code.getCode(),
                        code.getDateTimeOfPublication(),
                        code.getTimeLeft(),
                        code.getViewsLeft()));
        return ResponseEntity.ok().body(new ConcurrentHashMap<>(
                Map.of("id", String.valueOf(codeSnippet.getUuid()))));
    }

    @GetMapping("/api/code/{uuid}")
    public ResponseEntity getJsonSnippet(@PathVariable String uuid) {
        CodeSnippet apiCode = codeSnippetService.findCodeSnippetByUuid(UUID.fromString(uuid));
        if (deleteCodeSnippetByUuidIfNeeded(apiCode, UUID.fromString(uuid)) || apiCode == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Code snippet not found."));
        }
        long timeLeft = calculateTimeLeft(apiCode);
        return ResponseEntity.ok().body(new ConcurrentHashMap<>(
                Map.of("code", apiCode.getCode(),
                        "time", timeLeft < 0 ? 0 : timeLeft,
                        "views", apiCode.getViewsLeft() > 0 ? apiCode.getViewsLeft() - 1 : 0,
                        "date", apiCode.getFormattedDateTime())));
    }

    @Transactional
    @GetMapping("/code/{uuid}")
    public String getPlainSnippet(@PathVariable String uuid, Model model) {
        CodeSnippet apiCode = codeSnippetService.findCodeSnippetByUuid(UUID.fromString(uuid));
        if (!deleteCodeSnippetByUuidIfNeeded(apiCode, UUID.fromString(uuid))) {
            model.addAttribute("time", calculateTimeLeft(apiCode));
            model.addAttribute("views", apiCode.getViewsLeft());
            model.addAttribute("date", apiCode.getFormattedDateTime());
            model.addAttribute("code", apiCode.getCode());
        }
        return "apiCode";
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity getTenLatestJsonSnippets() {
        List<CodeSnippet> latestSnippets = codeSnippetService.findFirst10();
        List<Map<String , Object>> latestSnippetsFormatted = new CopyOnWriteArrayList<>();
        for (int i = 0; i < latestSnippets.size(); i++) {
            latestSnippetsFormatted.add(
                    Map.of("code", latestSnippets.get(i).getCode(),
                            "time", latestSnippets.get(i).getTimeLeft(),
                            "views", latestSnippets.get(i).getViewsLeft(),
                            "date", latestSnippets.get(i).getFormattedDateTime()));
        }
        return ResponseEntity.ok(latestSnippetsFormatted);
    }

    @GetMapping("/code/latest")
    public String getTenLatestPlainSnippets(Model model) {
        List<CodeSnippet> latestSnippets = codeSnippetService.findFirst10();
        model.addAttribute("snippets", latestSnippets);
        return "latestPlainText";
    }

    private int applyView(CodeSnippet apiCode) {
        int viewsLeft = apiCode.getViewsLeft();
        if (viewsLeft != 0) {
            apiCode.setViewsLeft(--viewsLeft);
        } else {
            --viewsLeft;
        }
        return viewsLeft;
    }

    private long calculateTimeLeft(CodeSnippet apiCode) {
        return apiCode.getTimeLeft() - Duration.between(apiCode.getDateTimeOfPublication(), LocalDateTime.now()).toSeconds();
    }

    private boolean deleteCodeSnippetByUuidIfNeeded(CodeSnippet apiCode, UUID uuid) {
        long secondsLeft = calculateTimeLeft(apiCode);
        int viewsLeft = applyView(apiCode);
        if (apiCode.getTimeLeft() != 0 && secondsLeft < 0 || viewsLeft == 0) {
            codeSnippetService.deleteCodeSnippetByUuid(uuid);
            return true;
        }
        return false;
    }
}

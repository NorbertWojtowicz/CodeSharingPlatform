package platform.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.persistence.CodeSnippetRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CodeSnippetService {
    private CodeSnippetRepository codeSnippetRepository;

    @Autowired
    public CodeSnippetService(CodeSnippetRepository codeSnippetRepository) {
        this.codeSnippetRepository = codeSnippetRepository;
    }

    public CodeSnippet findCodeSnippetByUuid(UUID uuid) {
        return codeSnippetRepository.findCodeSnippetByUuid(uuid);
    }

    public List<CodeSnippet> findFirst10() {
        return codeSnippetRepository.findFirst10ByIdNotNullAndTimeLeftIsAndViewsLeftIsOrderByDateTimeOfPublicationDesc(0, 0);
    }

    public void deleteCodeSnippetByUuid(UUID uuid) {
        codeSnippetRepository.deleteCodeSnippetByUuid(uuid);
    }

    public CodeSnippet save(CodeSnippet toSave) {
        return codeSnippetRepository.save(toSave);
    }
}

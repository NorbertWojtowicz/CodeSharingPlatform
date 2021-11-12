package platform.businesslayer;

import platform.ApiCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private UUID uuid = UUID.randomUUID();

    @Column
    private String code;

    @Column(name = "date")
    public LocalDateTime dateTimeOfPublication;

    @Column
    public long timeLeft;

    @Column
    public int viewsLeft;

    public CodeSnippet() {}

//    public CodeSnippet(String code, LocalDateTime dateTimeOfPublication) {
//        this.code = code;
//        this.dateTimeOfPublication = dateTimeOfPublication;
//    }

    public CodeSnippet(String code, LocalDateTime dateTimeOfPublication, long timeLeft, int viewsLeft) {
        this.code = code;
        this.dateTimeOfPublication = dateTimeOfPublication;
        this.timeLeft = timeLeft;
        this.viewsLeft = viewsLeft;
        System.out.println(uuid.toString());
    }

//    public CodeSnippet(String code, LocalDateTime dateTimeOfPublication, int viewsLeft) {
//        this.code = code;
//        this.dateTimeOfPublication = dateTimeOfPublication;
//        this.viewsLeft = viewsLeft;
//        this.timeLeft = 0;
//    }
//
//    public CodeSnippet(String code, LocalDateTime dateTimeOfPublication, long timeLeft) {
//        this.code = code;
//        this.dateTimeOfPublication = dateTimeOfPublication;
//        this.timeLeft = timeLeft;
//        this.viewsLeft = 0;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDateTimeOfPublication() {
        return dateTimeOfPublication;
    }

    public void setDateTimeOfPublication(LocalDateTime dateTimeOfPublication) {
        this.dateTimeOfPublication = dateTimeOfPublication;
    }

    public void refreshDateTime() {
        dateTimeOfPublication = LocalDateTime.now();
    }

    public String getFormattedDateTime() {
        return dateTimeOfPublication.toLocalDate() + " "
                + dateTimeOfPublication.toLocalTime().withNano(0);
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getViewsLeft() {
        return viewsLeft;
    }

    public void setViewsLeft(int viewsLeft) {
        this.viewsLeft = viewsLeft;
    }

    //    @Override
//    public int compareTo(ApiCode apiCode) {
//        return getFormattedDateTime().compareTo(apiCode.getFormattedDateTime()) * -1;
//    }
}

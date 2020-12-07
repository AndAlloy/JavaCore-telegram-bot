package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class NasaArticle {
    private String title;
    private String description;
    private String benefits;
    private int projects;

    public String titleToString() {
        return this.getTitle();
    }
    public String descriptionToString() {
        return this.getDescription();
    }
    public String benefitsToString() {
        return this.getBenefits();
    }

    public String[] toArray() {
        return new String[]{titleToString(), //0 - title string
                descriptionToString(), // 1 - description string
                benefitsToString()};//2 - benefits string
    }

}

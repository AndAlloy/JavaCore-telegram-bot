package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class NasaDayInHistory {
    private String explanation;
    private String url;

    public String explanationToString() {
        return this.getExplanation();
    }
    public String urlToString() {
        return this.getUrl();
    }

    public String[] toArray() {
        return new String[]{explanationToString(), //0 - text string
                            urlToString()};//1 - url string
    }
}

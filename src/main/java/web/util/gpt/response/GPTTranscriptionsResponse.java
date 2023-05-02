package web.util.gpt.response;

import lombok.Data;

@Data
public class GPTTranscriptionsResponse {

    private boolean ok;

    private String text;

}

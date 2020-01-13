package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.App;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppData {
    private Integer id;
    private String name;
    private String url;
    public AppData(App app){
        id = app.getId();
        name = app.getName();
        url = app.getUrl();
    }
}

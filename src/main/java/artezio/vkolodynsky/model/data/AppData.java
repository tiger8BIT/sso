package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.App;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppData {
    private Integer id;
    private String name;
    private String url;
    public static AppData from(App app){
        AppData appData = new AppData();
        appData.id = app.getId();
        appData.name = app.getName();
        appData.url = app.getUrl();
        return appData;
    }
}

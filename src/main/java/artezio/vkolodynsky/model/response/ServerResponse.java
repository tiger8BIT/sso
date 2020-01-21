package artezio.vkolodynsky.model.response;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

@Data
public final class ServerResponse {
    @NonNull private boolean success;
    @Nullable private String error;
    @Nullable private Object data;

    public static ServerResponse success(Object data){
        return new ServerResponse(true, null, data);
    }

    public static ServerResponse error(String error){
        return new ServerResponse(false, error, null);
    }

    private ServerResponse(@NonNull boolean success, @Nullable String error, @Nullable Object data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }
}

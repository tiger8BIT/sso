package artezio.vkolodynsky.model.response;

import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@Data
public final class ServerResponse {
    @NonNull private boolean success;
    @Nullable private String error;
    @Nullable private Object data;

    public static ResponseEntity success(Object data){
        return responseWrapper(new ServerResponse(true, null, data));
    }

    public static ResponseEntity error(String error){
        return responseWrapper(new ServerResponse(false, error, null));
    }

    private static ResponseEntity responseWrapper(ServerResponse serverResponse) {
        return ResponseEntity.status(HttpStatus.OK).body(serverResponse);
    }

    private ServerResponse(@NonNull boolean success, @Nullable String error, @Nullable Object data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }
}

package artezio.vkolodynsky.model.request;

import artezio.vkolodynsky.auth.SessionUtil;

public class CheckTokenRequest {
    public String token;
    public SessionUtil.UserDeviceInfo userDeviceInfo;
}

package artezio.vkolodynsky.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteAddress {
    @Id
    private String ipAddress;
    @ManyToOne
    private Session session;
}

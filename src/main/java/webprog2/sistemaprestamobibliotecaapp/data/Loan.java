package webprog2.sistemaprestamobibliotecaapp.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("LOAN")
public class Loan {
    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    @Transient  // Esta propiedad no se mapea a una columna de la base de datos
    @JsonIgnore // No incluir esta propiedad en las respuestas JSON de la API
    private List<Book> books;
    @Column("loan_time")
    private LocalDateTime loanTime = LocalDateTime.now();
    @Column("return_time")
    private LocalDateTime returnTime;
    private boolean active = true;
}

package elisonribeiro.provaApi.models;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class Livro {
    private Long id;
    private String nome;
    private String isbn;

    @ManyToMany
    private List<Autor> autores = new ArrayList<>();
}

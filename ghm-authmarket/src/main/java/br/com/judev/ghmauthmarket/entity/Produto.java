package br.com.judev.ghmauthmarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;

@Table(name = "tb_produto")
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 200, message = "A descrição não pode passar de 200 caracteres")
    @Column(nullable = false, length = 200)
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
    @Column(nullable = false)
    private Double preco;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior que 0")
    @Column(nullable = false)
    private Integer quantidade;

    @PastOrPresent(message = "A data de cadastro não pode ser futura")
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro = LocalDate.now();

//    @ManyToOne(cascade =  CascadeType.ALL)
//    @JoinColumn(name = "usuario_id", nullable = false)
//    private Usuario usuario;

    public Produto() {}

    public Produto(String nome, String descricao, Double preco, Integer quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.dataCadastro = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}

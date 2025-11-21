package service;

import contato.Contato;
import exception.ContatoExistenteException;
import exception.ContatoNaoEncontradoException;

import java.util.List;

public interface GerenciadorContatos {
    void adicionarContato(Contato contato) throws ContatoExistenteException;
    Contato buscarContato(String nome) throws ContatoNaoEncontradoException;
    void removerContato(String nome) throws ContatoNaoEncontradoException;
    List<Contato> listarTodosContatos();
    void salvarContatosCSV(String nomeArquivo);
    void carregarContatosCSV(String nomeArquivo);
    List<Contato> listarContatosOrdenados();
    List<Contato> buscarPorDominioEmail(String dominio);
}

package service;

import contato.Contato;
import exception.ContatoExistenteException;
import exception.ContatoNaoEncontradoException;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaManager implements GerenciadorContatos {

    private final List<Contato> contatos;

    public AgendaManager() {
        this.contatos = new ArrayList<>();
    }

    @Override
    public void adicionarContato(Contato contato) throws ContatoExistenteException {
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(contato.getNome())) {
                throw new ContatoExistenteException("Contato com o nome '" + contato.getNome() + "' já existe.");
            }
        }
        contatos.add(contato);
    }

    @Override
    public Contato buscarContato(String nome) throws ContatoNaoEncontradoException {
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        throw new ContatoNaoEncontradoException("Contato com o nome '" + nome + "' não encontrado.");
    }

    @Override
    public void removerContato(String nome) throws ContatoNaoEncontradoException {
        Contato contatoParaRemover = buscarContato(nome);
        contatos.remove(contatoParaRemover);
    }

    @Override
    public List<Contato> listarTodosContatos() {
        return new ArrayList<>(contatos);
    }

    @Override
    public void salvarContatosCSV(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Contato contato : contatos) {
                writer.write(String.format("%s;%s;%s%n", contato.getNome(), contato.getTelefone(), contato.getEmail()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar contatos no arquivo CSV: " + e.getMessage());
        }
    }

    @Override
    public void carregarContatosCSV(String nomeArquivo) {
        contatos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    contatos.add(new Contato(dados[0], dados[1], dados[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo CSV não encontrado: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao carregar contatos do arquivo CSV: " + e.getMessage());
        }
    }

    @Override
    public List<Contato> listarContatosOrdenados() {
        return contatos.stream()
                .sorted(Comparator.comparing(Contato::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contato> buscarPorDominioEmail(String dominio) {
        return contatos.stream()
                .filter(c -> c.getEmail().toLowerCase().endsWith("@" + dominio.toLowerCase()))
                .collect(Collectors.toList());
    }
}

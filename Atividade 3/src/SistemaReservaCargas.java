import java.util.TreeMap;
import javax.swing.JOptionPane;

class Carga {
    private String cnpj;
    private String nomeEmpresa;
    private String destino;
    private int peso;

    public Carga(String cnpj, String nomeEmpresa, String destino, int peso) {
        this.cnpj = cnpj;
        this.nomeEmpresa = nomeEmpresa;
        this.destino = destino;
        this.peso = peso;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return String.format("CNPJ: %s\nNome da Empresa: %s\nDestino: %s\nPeso: %d\n-------------------", cnpj, nomeEmpresa, destino, peso);
    }
}

class Navio {
    private TreeMap<String, Carga> cargas;
    private int capacidadeMaxima;

    public Navio(int capacidadeMaxima) {
        this.cargas = new TreeMap<>();
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public boolean reservarCarga(Carga carga) {
        int pesoTotal = 0;
        for (Carga c : cargas.values()) {
            pesoTotal += c.getPeso();
        }
        if (pesoTotal + carga.getPeso() > capacidadeMaxima) {
            return false;
        }
        cargas.put(carga.getCnpj(), carga);
        return true;
    }

    public Carga pesquisarCarga(String cnpj) {
        return cargas.get(cnpj);
    }

    public boolean excluirCarga(String cnpj) {
        return cargas.remove(cnpj) != null;
    }

    public String imprimirCargasReservadas() {
        StringBuilder result = new StringBuilder();
        for (Carga carga : cargas.values()) {
            result.append(carga.toString()).append("\n");
        }
        return result.toString();
    }
}

public class SistemaReservaCargas {
    private static void reservarCarga(Navio navio) {
        String cnpj = JOptionPane.showInputDialog("Digite o CNPJ da empresa");
        String nomeEmpresa = JOptionPane.showInputDialog("Digite o nome da empresa");
        String destino = JOptionPane.showInputDialog("Digite o destino da carga");

        int peso = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                peso = Integer.parseInt(JOptionPane.showInputDialog("Digite o peso da carga"));
                entradaValida = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Peso inválido! Por favor, insira novamente! (Capacidade máxima de 10000 quilos)");
            }
        }

        Carga carga = new Carga(cnpj, nomeEmpresa, destino, peso);

        if (navio.pesquisarCarga(cnpj) != null) {
            JOptionPane.showMessageDialog(null, "CNPJ já cadastrado!");
        } else if (navio.reservarCarga(carga)) {
            JOptionPane.showMessageDialog(null, "Carga reservada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível reservar a carga!");
        }
    }

    private static void pesquisarCarga(Navio navio) {
        String cnpj = JOptionPane.showInputDialog("Digite o CNPJ da empresa");
        Carga cargaPesquisada = navio.pesquisarCarga(cnpj);

        if (cargaPesquisada != null) {
            JOptionPane.showMessageDialog(null, cargaPesquisada.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Carga não encontrada!");
        }
    }

    private static void imprimirCargasReservadas(Navio navio) {
        JOptionPane.showMessageDialog(null, navio.imprimirCargasReservadas());
    }

    private static void excluirCarga(Navio navio) {
        String cnpj = JOptionPane.showInputDialog("Digite o CNPJ da empresa");

        if (navio.excluirCarga(cnpj)) {
            JOptionPane.showMessageDialog(null, "Carga excluída com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Carga não encontrada!");
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "---------------------------------------------------\r\n"
                + "|  SISTEMA DE RESERVA DE CARGA  |        \r\n"
                + "---------------------------------------------------");

        String[] opcoes = { "Iniciar", "Sair" };
        int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção", "SISTEMA DE RESERVA DE CARGA",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao == 0) {
            Navio navio = new Navio(10000);

            String menu = "Sistema de Reserva de Cargas\n1. Reservar Carga\n2. Pesquisar Carga\n3. Imprimir Cargas Reservadas\n4. Excluir Carga\n5. Finalizar";
            String opcaoMenu = "";

            while (!opcaoMenu.equals("5")) {
                opcaoMenu = JOptionPane.showInputDialog(null, menu, "Sistema de Reserva de Cargas",
                        JOptionPane.PLAIN_MESSAGE);

                switch (opcaoMenu) {
                    case "1":
                        reservarCarga(navio);
                        break;

                    case "2":
                        pesquisarCarga(navio);
                        break;

                    case "3":
                        imprimirCargasReservadas(navio);
                        break;

                    case "4":
                        excluirCarga(navio);
                        break;

                    case "5":
                        JOptionPane.showMessageDialog(null, "Sistema fechado com sucesso!");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                        break;
                }
            }
        } else if (opcao == 1) {
            JOptionPane.showMessageDialog(null, "Sistema fechado com sucesso!");
        }
    }
}

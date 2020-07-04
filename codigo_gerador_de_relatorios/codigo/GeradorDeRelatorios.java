import java.awt.Color;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;

public class GeradorDeRelatorios {

	private Produto [] produtos;

	private ComportamentoDeOrdenacao co = null;
	private ComportamentoDeCriterio cc = null;
	private ComportamentoDeFiltro cf = null;
	private HashMap<Integer, Formatador> formatacoes;

	public GeradorDeRelatorios(Produto [] produtos){

		this.produtos = new Produto[produtos.length];
		
		for(int i = 0; i < produtos.length; i++){
		
			this.produtos[i] = produtos[i];
		}

		this.formatacoes = new HashMap<>();

	}

	// Getters

	public Produto[] getProdutos(){
		return this.produtos;
	}

	public ComportamentoDeOrdenacao getCo(){
		return this.co;
	}

	public ComportamentoDeCriterio getCc(){
		return this.cc;
	}

	public ComportamentoDeFiltro getCf(){
		return this.cf;
	}

	public HashMap<Integer, Formatador> getFormatacoes(){
		return this.formatacoes;
	}

	// Setters

	public void setOrdenacao(ComportamentoDeOrdenacao co){
		this.co = co;
	}

	public void setCriterio(ComportamentoDeCriterio cc){
		this.cc = cc;
	}

	public void setFiltro(ComportamentoDeFiltro cf){
		this.cf = cf;
	}

	public void setFormatacoes(int id, Formatador f){
		this.formatacoes.put(id, f);
	}
	public void setFormatacoes(Produto p, Formatador f){
		this.formatacoes.put(p.getId(), f);
	}

	// Metodos

	private void ordena(int ini, int fim){

		if(this.co == null)
			throw new RuntimeException("Algoritmo nao definido!");

		if(this.cc == null)
			throw new RuntimeException("Criterio nao definido!");

		co.ordenacao(ini, fim, this);
	}
	
	public void geraRelatorio(String arquivoSaida) throws IOException {

		if(this.cf == null)
			throw new RuntimeException("Filtro nao definido!");

		ordena(0, produtos.length - 1);

		PrintWriter out = new PrintWriter(arquivoSaida);

		out.println("<!DOCTYPE html><html>");
		out.println("<head><title>Relatorio de produtos</title></head>");
		out.println("<body>");
		out.println("Relatorio de Produtos:");
		out.println("<ul>");

		int count = 0;

		for(int i = 0; i < produtos.length; i++){

			Produto p = produtos[i];
			boolean selecionado = false;

			selecionado = cf.filtrar(p);

			if(selecionado){
				//se não foi adicionado uma formatação no hashmap para o produto, f vai receber FormatacaoPadrao
				Formatador f = formatacoes.get(p.getId()) == null ? new FormatacaoPadrao(): formatacoes.get(p.getId());

				out.print("<li>");
				out.println(f.formatar(p));
				out.println("</li>");
				count++;
			}
		}

		out.println("</ul>");
		out.println(count + " produtos listados, de um total de " + produtos.length + ".");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	public static Produto [] carregaProdutos(){

		return new Produto [] { 

			new ProdutoPadrao( 1, "O Hobbit", "Livros", 2, 34.90),
			new ProdutoPadrao( 2, "Notebook Core i7", "Informatica", 5, 1999.90),
			new ProdutoPadrao( 3, "Resident Evil 4", "Games", 7, 79.90),
			new ProdutoPadrao( 4, "iPhone", "Telefonia", 8, 4999.90),
			new ProdutoPadrao( 5, "Calculo I", "Livros", 20, 55.00),
			new ProdutoPadrao( 6, "Power Glove", "Games", 3, 499.90),
			new ProdutoPadrao( 7, "Microsoft HoloLens", "Informatica", 1, 19900.00),
			new ProdutoPadrao( 8, "OpenGL Programming Guide", "Livros", 4, 89.90),
			new ProdutoPadrao( 9, "Vectrex", "Games", 1, 799.90),
			new ProdutoPadrao(10, "Carregador iPhone", "Telefonia", 15, 499.90),
			new ProdutoPadrao(11, "Introduction to Algorithms", "Livros", 7, 315.00),
			new ProdutoPadrao(12, "Daytona USA (Arcade)", "Games", 1, 12000.00),
			new ProdutoPadrao(13, "Neuromancer", "Livros", 5, 45.00),
			new ProdutoPadrao(14, "Nokia 3100", "Telefonia", 4, 249.99),
			new ProdutoPadrao(15, "Oculus Rift", "Games", 1, 3600.00),
			new ProdutoPadrao(16, "Trackball Logitech", "Informatica", 1, 250.00),
			new ProdutoPadrao(17, "After Burner II (Arcade)", "Games", 2, 8900.0),
			new ProdutoPadrao(18, "Assembly for Dummies", "Livros", 30, 129.90),
			new ProdutoPadrao(19, "iPhone (usado)", "Telefonia", 3, 3999.90),
			new ProdutoPadrao(20, "Game Programming Patterns", "Livros", 1, 299.90),
			new ProdutoPadrao(21, "Playstation 2", "Games", 10, 499.90),
			new ProdutoPadrao(22, "Carregador Nokia", "Telefonia", 14, 89.00),
			new ProdutoPadrao(23, "Placa Aceleradora Voodoo 2", "Informatica", 4, 189.00),
			new ProdutoPadrao(24, "Stunts", "Games", 3, 19.90),
			new ProdutoPadrao(25, "Carregador Generico", "Telefonia", 9, 30.00),
			new ProdutoPadrao(26, "Monitor VGA 14 polegadas", "Informatica", 2, 199.90),
			new ProdutoPadrao(27, "Nokia N-Gage", "Telefonia", 9, 699.00),
			new ProdutoPadrao(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica", 23, 49.00),
			new ProdutoPadrao(29, "Alone in The Dark", "Games", 11, 59.00),
			new ProdutoPadrao(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00),
			new ProdutoPadrao(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00),
			new ProdutoPadrao(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00)
		};
	} 

	public static void main(String [] args) {
	
		Produto [] produtos = carregaProdutos();

		GeradorDeRelatorios gdr;

		gdr = new GeradorDeRelatorios(produtos);
		
		ComportamentoDeCriterio pc = new CriterioPrecoDecr();
		ComportamentoDeOrdenacao is = new OrdenacaoQuickSort();
		ComportamentoDeFiltro fc = new FiltroCategoriaIgual("Informatica");

		gdr.setOrdenacao(is);
		gdr.setCriterio(pc);
		gdr.setFiltro(fc);

		Formatador f = new FormatacaoPadrao();

		gdr.setFormatacoes(32, new BoldDecorator(f));
		gdr.setFormatacoes(23, new BoldDecorator(f));
		gdr.setFormatacoes(28, new CorDecorator(f, Color.RED));
		gdr.setFormatacoes(28, new CorDecorator(f, Color.BLUE));
		gdr.setFormatacoes(7, new CorDecorator(f, Color.RED));

		try{
			gdr.geraRelatorio("saida.html");
		}
		catch(IOException e){
			
			e.printStackTrace();
		}
	}
}

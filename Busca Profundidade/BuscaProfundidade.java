package io.wveiga.ia.algs.busca.naoinfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import io.wveiga.ia.problema.Acao;
import io.wveiga.ia.problema.Problema;


public class BuscaProfundidade<S, T extends Problema<S>> extends BuscaNaoInformada<S, T>{
	

	public BuscaProfundidade(T problema) {
		super(problema);
	}

	@Override
	public Borda<S> criaBorda() {
		return new BordaPilha<>();
	}
	
	
	
	public Optional<List<Acao<S>>> buscaRecursiva() {
		
		T problema = getProblema();
		
		Queue<Acao<S>> caminho = new LinkedList<>();

		Set<S> fechados = new HashSet<>();
		fechados.add(problema.estadoInicial());

		buscaRecusiva(Acao.nenhuma(problema.estadoInicial()), caminho, fechados);
		
		Optional<List<Acao<S>>> solucao = Optional.empty();
		if (!caminho.isEmpty()) {
			solucao = Optional.of(new ArrayList<>(caminho));
		}
		
		return solucao;
	}
	

	private boolean buscaRecusiva(Acao<S> acaoAtual, Queue<Acao<S>> caminho, Set<S> fechados ) {
		
		T problema = getProblema();
		
		S alvo = acaoAtual.getEstadoAlvo();

		caminho.offer(acaoAtual);
		fechados.add(alvo);
		
		if (problema.solucao(alvo)) {
			return true;
		}
		
		List<Acao<S>> sucessores = problema.sucessores(alvo);
		for(Acao<S> proximaAcao : sucessores){
			if (!fechados.contains(proximaAcao.getEstadoAlvo())){
				if(buscaRecusiva(proximaAcao, caminho, fechados)){
					return true;
				}
			}
		}
		fechados.remove(alvo);
		caminho.poll();
		
		return false;
	}
	
	private static class BordaPilha<S> implements Borda<S> {
		
		private final LinkedList<Acao<S>> pilha = new LinkedList<>();
		
		@Override
		public void insere(Acao<S> acao) {
			pilha.addLast(acao);
		}


		@Override
		public Acao<S> retira() {
			if (vazia()){
				throw new IllegalStateException("Fila vazia");
			} 
			return pilha.removeLast();
		}
		

		@Override
		public boolean vazia() {
			return pilha.isEmpty();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((pilha == null) ? 0 : pilha.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BordaPilha<?> other = (BordaPilha<?>) obj;
			if (pilha == null) {
				if (other.pilha != null)
					return false;
			} else if (!pilha.equals(other.pilha))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "BordaPilha [pilha=" + pilha + "]";
		}
				
	}	
}

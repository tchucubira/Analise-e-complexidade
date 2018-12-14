
import representacoes.*; 
import java.util.LinkedList; 
import java.util.List; 

public class BFS{

    private LinkedList<Integer> fila = new LinkedList<Integer>();
    private int caminho[];
    private int analisado[];
    private int tamMatriz;

    
    private void verificarTamMatriz(MatrizAdjacencia M){
	tamMatriz = M.getTamanhoDaMatriz();
    }

    
    private void iniciandoVetorAnalisado(){
	analisado = new int[tamMatriz];
	for (int i = 0; i < tamMatriz; i++){
	    analisado[i] = -1;
	}
    }
   
    private void chaveInicial(int chave){
	analisado[chave] = chave;
    }

  
    private boolean chaveFoiAnalisada(int chave){
        if (analisado[chave] != -1) return true;
	else return false;
    }
    
   
    private int quantVizinhos(int chave, MatrizAdjacencia M){
	int cont = 0;
	for (int i = 0; i < tamMatriz; i++){
	    if (M.M[chave][i] == 1) cont++;
	}
	return cont;
    }
    
   
    private int[] vizinhos(int chave, MatrizAdjacencia M){
	int[] _vizinhos;
	int qVizinhos = quantVizinhos(chave, M);
	if (qVizinhos == 0) _vizinhos = new int[0];
	else{
	    _vizinhos = new int[qVizinhos];
	    int cont = 0;
	    for (int i = 0; i < tamMatriz; i++){
		if ((M.M[chave][i] == 1) && 
		    (!chaveFoiAnalisada(i))){
		    _vizinhos[cont] = i;
		    analisado[i] = chave;
		    cont++;
		}
	    }
	    if (qVizinhos != cont){
		int[] aux = new int[cont];
		for (int i = 0; i < cont; i++){
		    aux[i] = _vizinhos[i];
		}
		return aux;
	    }
	}
	return _vizinhos;
    }

    
    private boolean atingido(int j, int[] vetorVizinhos){
	for (int i = 0; i < vetorVizinhos.length; i++){
	    System.out.printf("%d", vetorVizinhos[i]);
	    System.out.println();
	    if (vetorVizinhos[i] == j) return true;
	}
	return false;
    }

    
    private void empilhandoVizinhos(int[] vetorVizinhos, int chave){
	for (int i = 0; i < vetorVizinhos.length; i++){
	    if (chaveFoiAnalisada(vetorVizinhos[i])){
		fila.addLast(vetorVizinhos[i]);
	    }
	}
    }

   
    private void imprimirVetorAnalisado(){
	for (int i = 0; i < tamMatriz; i++){
	    System.out.printf("%d ", analisado[i]);
	}
    }
    
  
    private int quantElementosCaminho(int i, int j){
	int inicio = j;
	int cont = 0;
	while(inicio != i){
	    cont++;
	    inicio = analisado[inicio];
	}
	return cont;
    }
    
    
    private void armazenandoCaminho(int i, int j){
	int qtdCaminho = quantElementosCaminho(i, j);
	caminho = new int[qtdCaminho+1];
	caminho[0] = j;
	int inicio = j;
	int cont = 1;
	while(inicio != i){
	    caminho[cont] = analisado[inicio];
	    inicio = analisado[inicio];
	    cont++;
	}
    }

    
    private void imprimirCaminho(){
	System.out.println();
        for (int i = caminho.length - 1; i > -1; i--){
	    System.out.printf("%d ", caminho[i]);
	}
	System.out.println();
    }

    
    public void Bfs(int i, int j, MatrizAdjacencia M){
	int valor_invertido = 0;
	verificarTamMatriz(M); 
	iniciandoVetorAnalisado();
	chaveInicial(i);
	fila.addLast(i);
	while(fila.size() != 0){
	    int v = fila.removeFirst();
	    int[] viz = vizinhos(v, M);
	   
	    System.out.printf("Impressao do vetor analisado de %d!\n", v);
	    imprimirVetorAnalisado();
	    System.out.println();
	    if (atingido(j, viz)){
		System.out.println("Axei!");
		imprimirVetorAnalisado();
		armazenandoCaminho(i, j);
	        imprimirCaminho();
		return;
	    }
	    else{
		
		empilhandoVizinhos(viz, v);
	    }
	}
    }

}

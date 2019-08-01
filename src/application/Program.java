package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Product;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		//digite caminho do arquivo
		System.out.println("Enter full file path: ");
		String path = sc.nextLine();//lê o caminho do arquivo no path
		
		//abrindo o arquivo
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			//Instancia lista produtos
			List<Product> list = new ArrayList<>();
			
			//lê uma linha
			String line = br.readLine();
			while(line != null) {
				//split recorta string em dois e de forma q acessa nome e preço
				String[] fields = line.split(",");	
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));//instancia prod. fields posição 0 é o nome e fiels pos. 1 é o preço
				line = br.readLine();//lê a próxima linha. E assim por diante até acabar o arquivo
			}
			
			//preço médio dos produtos
			double avg = list.stream()//converte lista para stream
					.map(p -> p.getPrice())//novo stream só o preço dos prod
			        .reduce(0.0, (x,y) -> x+y) / list.size(); //soma todos produtos e divide pelo tamanho da lista
			
			System.out.println("Average price: " + String.format("%.2f", avg));
			
			//função que compara dois String indepente de letras maiúscula ou minúsculas
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase()); 
			
			//Nova lista de String
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg)//filtrar todos produtos menor que o valor avg
					.map(p -> p.getName())//nova stream com o nome dos produtos filtrados em cima
					.sorted(comp.reversed())//ordena em ordem decrescente alfabética
					.collect(Collectors.toList());//transformar String em lista
			
			//mostrar nomes na tela
			names.forEach(System.out::println);
					
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}

}

package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {

		String path = JOptionPane.showInputDialog("Enter file path:");

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Product> list = new ArrayList<>();
			String line;
			while ((line = br.readLine()) != null) {
				String fields[] = line.split(",");
				String productName = fields[0];
				double productPrice = Double.parseDouble(fields[1]);
				list.add(new Product(productName, productPrice));
			}

			StringBuilder sb = new StringBuilder();

			double avg = list.stream().mapToDouble(p -> p.getPrice()).average().orElse(0.0);

			sb.append("Price average = $ " + String.format("%.2f", avg) + "\n");

			List<String> names = list.stream().filter(p -> p.getPrice() < avg).map(Product::getName)
					.sorted(Comparator.reverseOrder()).collect(Collectors.toList());

			for (String n : names) {
				sb.append(n).append("\n");
			}

			JOptionPane.showMessageDialog(null, sb);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}

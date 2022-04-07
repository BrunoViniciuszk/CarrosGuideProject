package sp.senai.br.cotia.cfp138.carrosguide.util;

import java.io.IOException;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class FirebaseUtil {
	// variável para guardar as credenciais do firebase
	private Credentials credenciais;
	// variavel para acessar o storage
	private Storage storage;
	// constante para o nome do bucket
	private final String BUCKET_NAME = "carrosguide.appspot.com";
	// constante para o prefixo da url
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	// constante para o sufixo da url
	private final String SUFFIX = "?alt=media";
	// constante para a url
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		// buscar as credenciais (arquivo JSON)
		Resource resource = new ClassPathResource("chavefirebase.json");
		try {
			// ler o arquivo para obter as credenciais
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// acessa o serviço de storage
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public String uploadFile(MultipartFile arquivo) {
		// gera uma String aleatória para o nome do arquivo
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		return nomeArquivo;
	}
	
	
	// retorna a extensao de um arquivo atravez do seu nome
	private String getExtensao(String nomeArquivo) {
		// retorna o trecho da string que vai do ultimo ponto ate o fim
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
}

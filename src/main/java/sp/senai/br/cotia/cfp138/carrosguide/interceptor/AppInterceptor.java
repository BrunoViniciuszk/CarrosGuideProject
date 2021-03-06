package sp.senai.br.cotia.cfp138.carrosguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import sp.senai.br.cotia.cfp138.carrosguide.annotation.Privado;
import sp.senai.br.cotia.cfp138.carrosguide.annotation.Publico;
import sp.senai.br.cotia.cfp138.carrosguide.rest.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// variavel para descobrir pra onde estao tentando ir
		String uri = request.getRequestURI();

		// mostra a uri
		System.out.println(uri);

		// verifica se o handler é um HandlerMethod
		// o que indica que foi encontrado um metodo em algum controller
		if (handler instanceof HandlerMethod) {
			// liberar o acesso a pagina inicial
			if (uri.equals("/")) {
				return true;
			}
			if (uri.endsWith("/error")) {
				return true;
			}

			// faz o casting para HandlerMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;

			// libera a api de carro
			if (uri.startsWith("/api")) {

				// variavel para o token
				String token = null;

				// quando for api
				// se for um metodo privado
				if (metodoChamado.getMethodAnnotation(Privado.class) != null) {
					try {
					// obtem o token da request
					token = request.getHeader("Authorization");
					// algoritmo para descriptografar
					Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
					// objeto para verificar o token
					JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
					DecodedJWT jwt = verifier.verify(token);
					// extrair os dados do payload
					Map<String, Claim> payloadMap = jwt.getClaims();
					System.out.println(payloadMap.get("nome_usuario"));
					return true;
					}catch (Exception e) {
						if(token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value()); 
							e.getMessage();
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value());
							e.getMessage();
						}
						return false; 
					}
				}

			} else {

				// se o metodo for publico, libera
				if (metodoChamado.getMethodAnnotation(Publico.class) != null) {
					return true;
				}

				// verificar se existe um usuario logado
				if (request.getSession().getAttribute("usuarioLogado") != null) {
					return true;

				} else {

					// redireciona para a pagina inicial
					response.sendRedirect("/");
					return false;
				}
			}
		}
		return true;
	}

}

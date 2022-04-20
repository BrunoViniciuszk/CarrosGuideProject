package sp.senai.br.cotia.cfp138.carrosguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import sp.senai.br.cotia.cfp138.carrosguide.annotation.Publico;

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

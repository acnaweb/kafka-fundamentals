package io.github.acnaweb.poc.kafka;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

public class NewOrderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3896702928889230802L;

	private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<Order>();
	private final KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<String>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String email = req.getParameter("email");

			String orderId = UUID.randomUUID().toString();
			BigDecimal amount = new BigDecimal(req.getParameter("valor"));

			Order value = new Order(orderId, email, amount);
			orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, value);

			emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, "Obrigado pelo seu pedido");

			resp.setStatus(HttpStatus.OK_200);
			resp.getWriter().println("Pedido enviado");
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	@Override
	public void destroy() {
		super.destroy();
		orderDispatcher.close();
		emailDispatcher.close();
	}

}

package myoptaplannerexamples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The controller shows the list of examples and info about author.
 * 
 * @author Jiri Petrlik
 *
 */
@Controller
public class IndexController {
	
	/**
	 * The list of the examples.
	 * @return
	 */
	@RequestMapping("/")
	public String indexAction() {
		return "index/index";
	}
}

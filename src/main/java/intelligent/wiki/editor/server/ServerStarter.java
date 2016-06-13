/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

package intelligent.wiki.editor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for starting wiki editor server.
 *
 * @author Myroslav Rudnytskyi
 * @version 20.05.2016
 */
@RestController
@SpringBootApplication
public class ServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(ServerStarter.class, args);
	}

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
}

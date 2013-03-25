<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<%--<div class="ym-grid linearize-level-1">
		<div class="ym-column linearize-level-1">
			<div class="ym-col1">
				<div class="ym-cbox">
					<section class="box info">
						<h2 th:text="#{home.welcome}">Welcome to Spring Movies!</h2>
						<p th:text="#{home.presentation}"></p>
					</section>
					<section class="ym-grid linearize-level-2">
						<article class="ym-g40 ym-gl">
							<div class="ym-gbox">
								<h3 th:text="#{home.sidebar.title}">Browse movies</h3>
								<p th:text="#{home.sidebar.text}"></p>
							</div>
						</article>
						<article class="ym-g60 ym-gr">
							<div class="ym-gbox" th:if="${sec.isAnonymous()}">
								<h3 th:text="#{login.title}">Sign in</h3>
								<p>	
									New user? <a href="#" th:href="@{/auth/signup}">Sign up here</a> (FREE)
								</p>
								<form class="ym-form" action="#" th:action="@{/spring-mvc-movies/j_spring_security_check}" method="post">
									<div class="ym-fbox-text">
										<label for="j_username" th:text="#{login.username}">Username:</label>
				  						<input type="text" name="j_username" id="j_username" size="20" />
			  						</div>
			  						
			  						<div class="ym-fbox-text">
										<label for="j_password" th:text="#{login.password}">Password:</label>
				  						<input type="password" name="j_password" id="j_password" size="20" />
			  						</div>
			  						
			  						<div class="ym-fbox-check">
			  							<input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" />
			  							<label for="_spring_security_remember_me" th:text="#{login.remember.me}">Remember me</label>
									</div>
									
									<div class="ym-fbox-button">
										<input type="submit" class="ym-button" id="submit" value="login" name="submit" th:value="#{login.button.label}" />
										<input type="reset" class="ym-button" id="reset" value="reset" name="reset" th:value="#{signup.reset.label}" />
									</div>
								</form>
							</div>
						</article>
					</section>
				</div>
			</div>
			<aside class="ym-col3">
				<div class="ym-cbox">
					<h2 th:text="#{home.movies}">Movies</h2>
					<h4 th:text="#{home.last.update}">Last update</h4>
					<div class="ym-contain-dt" th:each="movie : ${movies}">
						<img width="120px" th:src="@{'/images/posters/' + ${movie.id}}" alt="ads"
							class="float-left bordered" />
					</div>
				</div>
			</aside>
		</div>
	</div>--%>
</body>
</html>

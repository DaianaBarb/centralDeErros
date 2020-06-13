package com.codenation.curso.central.error.configuration;

public class SecurityConstants {
	
static final String SING_UP_URL="/swagger-ui.html";
public static final String SECRET ="DAIANABARBOSA";
//Authorization beares haakaoauahatga (hash)
public static final String TOKEN_PREFIX="Bearer";
public static final String HEADER_STRING="Authorization";
//Expira em um dia 86400000 mile segundos
public static final Long EXPIRATION_TIME=86400000L;
}

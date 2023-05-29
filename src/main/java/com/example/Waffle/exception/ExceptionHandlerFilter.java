package com.example.Waffle.exception;

import com.example.Waffle.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);
        }catch(UserException e){
            setResponse(response, e);
        }
    }

    private void setResponse(HttpServletResponse response, UserException e){

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(e.getErrorCode().getHttpStatus().value());
        response.setContentType("application/json;charset=utf-8");
        ResponseDto errorResponse = new ResponseDto(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getCode(), e.getErrorCode().getMessage());

        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

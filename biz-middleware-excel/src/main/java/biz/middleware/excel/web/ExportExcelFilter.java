package biz.middleware.excel.web;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExportExcelFilter extends OncePerRequestFilter {

    MediaType supportedMediaType = MediaType.valueOf("application/vnd.ms-excel");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accept = request.getHeader("Accept");
        if (StringUtils.isEmpty(accept)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (accept.toLowerCase().contains("application/vnd.ms-excel")) {
            try {
                ExportExcelContext context = ExcelContextHolder.newContext();

                filterChain.doFilter(request, response);
            } finally {
                ExcelContextHolder.clear();
            }

        }
    }
}

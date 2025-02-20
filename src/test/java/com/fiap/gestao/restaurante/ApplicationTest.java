package com.fiap.gestao.restaurante;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTest {

    @Test
    @DisplayName("Deve executar o método main da aplicação")
    void shouldExecuteMainMethod() {
        Application.main(new String[] {});
    }
}
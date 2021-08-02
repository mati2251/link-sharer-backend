package io.mateusz.linksharer.link

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.IanaLinkRelations
import kotlin.jvm.Throws

@SpringBootTest
class LinkAssemblerTests {

    @Autowired
    private lateinit var assembler: LinkAssembler

    @Test
    @Throws(Exception::class)
    fun isNotNull(){
        val link = Link("test", "test")
        val entityModel = assembler.toModel(link)
        assertThat(entityModel.getLink("all")).isNotNull
        assertThat(entityModel.getLink("container")).isNotNull
        assertThat(entityModel.getLink(IanaLinkRelations.SELF)).isNotNull
        assertThat(entityModel.content).isNotNull
        assertThat(entityModel).isNotNull
    }
}
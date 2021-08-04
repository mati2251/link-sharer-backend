package io.mateusz.linksharer.link

import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.*
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LinkControllerTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var linksContainerService: LinksContainerService

    @Autowired
    private lateinit var linksService: LinkService

    @Test
    @Throws(Exception::class)
    fun getLinks() {
        val response = restTemplate.getForObject(this.getUrl(), CollectionModel::class.java)
        assertThat(response).isNotNull
        assertThat(response.content).isNotNull
        assertThat(response.links.getLink(IanaLinkRelations.SELF)).isNotNull
    }

//    @Test
//    @Throws(Exception::class)
//    fun postLinks() {
//        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
//        val json = "{" +
//                "  \"title\": \"fb\"," +
//                "  \"url\": \"http.\"" +
//                "}"
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//        val request = HttpEntity(json, headers)
//        val response = restTemplate.postForObject("${this.getUrl()}/container/${container.id}", request, EntityModel::class.java)
//        assertThat(response).isNotNull
////        assertThat(response.content.toString()).contains("fb")
////        assertThat(response.content.toString()).contains("http.")
////        assertThat(response.links.getLink(IanaLinkRelations.SELF))
////        assertThat(response.links.getLink("all"))
////        assertThat(response.links.getLink("container"))
//    }

    fun getUrl(): String {
        return "http://localhost:$port/api/v1/link"
    }
}
package io.mateusz.linksharer.linkscontainer

import com.fasterxml.jackson.databind.ObjectMapper
import io.mateusz.linksharer.Endpoints
import io.mateusz.linksharer.link.LinkService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class EntityModelContainer : EntityModel<LinksContainer>()

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LinksContainerControllerTests {

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var linksContainerService: LinksContainerService


    @Test
    @Throws(Exception::class)
    fun postLinksContainer() {
        val container = LinksContainer("test", "description")
        val json = ObjectMapper().writeValueAsString(container).toString()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity(json, headers)
        val response: EntityModelContainer =
            restTemplate.postForObject(this.getUrl(), request, EntityModelContainer::class)!!
        assertContainers(container, response)
        linksContainerService.deleteLinksContainer(response.content!!.id)
    }

    @Test
    @Throws(Exception::class)
    fun getLinksContainerById(){
        val container = linksContainerService.createLinksContainer(LinksContainer("title", "description"))
        val response  = restTemplate.getForObject("${this.getUrl()}/${container.id}", EntityModelContainer::class.java)
        assertThat(response).isNotNull
        assertThat(response.content).isEqualTo(container)
        this.assertContainersLinks(response)
        this.linksContainerService.deleteLinksContainer(container)
    }

    @Test
    @Throws(Exception::class)
    fun getLinksContainers(){
        val response = restTemplate.getForObject(this.getUrl(), CollectionModel::class.java)
        assertThat(response).isNotNull
        assertThat(response.content).isNotNull
        assertThat(response.links.getLink(IanaLinkRelations.SELF)).isNotNull
    }

    private fun assertContainers(originalContainer: LinksContainer, response: EntityModelContainer) {
        assertThat(response).isNotNull
        assertThat(response.content?.title).isEqualTo(originalContainer.title)
        assertThat(response.content?.description).isEqualTo(originalContainer.description)
        this.assertContainersLinks(response)
    }

    private fun assertContainersLinks(container: EntityModel<*>){
        assertThat(container.links.getLink(IanaLinkRelations.SELF)).isNotNull
        assertThat(container.links.getLink("all")).isNotNull
    }

    private fun getUrl(): String {
        return "${Endpoints.BASE_URL}:$port/${Endpoints.LINKS_CONTAINER_CONTROLLER_PATH}"
    }
}
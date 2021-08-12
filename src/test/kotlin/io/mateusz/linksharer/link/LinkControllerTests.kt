package io.mateusz.linksharer.link

import com.fasterxml.jackson.databind.ObjectMapper
import io.mateusz.linksharer.Endpoints
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
import java.util.stream.Collectors


class EntityModelLink : EntityModel<Link>()
class CollectionModelLinks : CollectionModel<EntityModel<Link>>()

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

    @Test
    @Throws(Exception::class)
    fun getLinksById() {
        val container = linksContainerService.createLinksContainer(LinksContainer("test", "test"))
        val link = Link("test", "description", container)
        linksService.createLink(link)
        val response: EntityModel<Link> =
            restTemplate.getForObject("${this.getUrl()}/${link.id}", EntityModelLink::class.java)
        this.assertLinks(response, link)
        val id: Long = link.id
        linksService.deleteLink(id)
        linksContainerService.deleteLinksContainer(container)
    }

    fun assertLinks(entityModel: EntityModel<Link>, link: Link) {
        assertThat(entityModel).isNotNull
        assertThat(entityModel.content?.url).contains(link.url)
        assertThat(entityModel.content?.title).contains(link.title)
        assertThat(entityModel.links.getLink(IanaLinkRelations.SELF))
        assertThat(entityModel.links.getLink("all"))
        assertThat(entityModel.links.getLink("container"))
    }

    private fun getUrl(): String {
        return "${Endpoints.BASE_URL}:$port/${Endpoints.LINK_CONTROLLER_PATH}"
    }
}
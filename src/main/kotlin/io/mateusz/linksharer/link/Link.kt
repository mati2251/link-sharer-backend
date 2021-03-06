package io.mateusz.linksharer.link

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.mateusz.linksharer.linkscontainer.LinksContainer
import io.mateusz.linksharer.linkscontainer.LinksContainerAssembler
import org.springframework.hateoas.EntityModel
import javax.persistence.*

@JsonIgnoreProperties("linksContainer")
@Entity
@Table(name = "Link")
class Link {
    @Id
    @SequenceGenerator(
        name = "link_sequence",
        sequenceName = "link_sequence",
        allocationSize = 1,
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_sequence")
    var id: Long = 0
    var title: String = ""
    var url: String = ""

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "container_id", nullable = false)
    private var linksContainer: LinksContainer = LinksContainer()

    constructor(title: String, url: String, linksContainer: LinksContainer) {
        this.title = title
        this.url = url
        this.linksContainer = linksContainer
    }

    constructor(title: String, url: String) {
        this.title = title
        this.url = url
    }

    fun getLinksContainer(): EntityModel<LinksContainer> {
        val assembler = LinksContainerAssembler()
        val tmpLinksContainer = this.linksContainer
        tmpLinksContainer.setLinks(null)
        return assembler.toModel(tmpLinksContainer)
    }

    fun setLinksContainer(linksContainer: LinksContainer) {
        this.linksContainer = linksContainer
    }

    fun getContainerId(): Long {
        return this.linksContainer.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Link

        if (id != other.id) return false
        if (title != other.title) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        return try {
            var result = id.hashCode()
            result = 31 * result + title.hashCode()
            result = 31 * result + url.hashCode()
            result
        } catch (ex: NullPointerException){
            0
        }
    }

    override fun toString(): String {
        return "Link(id=$id, title='$title', url='$url')"
    }
}
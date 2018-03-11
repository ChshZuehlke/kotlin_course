package ch.zuehlke.sbb.reddit.models

import ch.zuehlke.sbb.reddit.features.news.overview.adapter.AdapterConstants
import ch.zuehlke.sbb.reddit.features.news.overview.adapter.ViewType

/**
 * Created by chsc on 08.11.17.
 */
//TODO: room_exercise1: Füge die nötige Room Annotation hinzu um eine Entity zu erstellen
data class RedditNewsData(
        var author: String? = null,
        var title: String? = null,
        var numberOfComments: Int = 0,
        var created: Long = 0,
        var thumbnailUrl: String? = "",
        var url: String? = "",
        //TODO: room_exercise1: Das ist ein Primary Key. Füge die nötige Room Annotation hinzu
        var permaLink: String = ""

): ViewType{
    override fun getViewType(): Int {
        return AdapterConstants.NEWS
    }
}

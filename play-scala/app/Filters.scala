import javax.inject.Inject
import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

class Filters @Inject()(
  corsFilter: CORSFilter
) extends HttpFilters {
  override def filters = Seq(corsFilter)
}

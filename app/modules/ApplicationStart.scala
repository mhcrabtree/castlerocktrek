package modules

import scala.concurrent.Future
import javax.inject.{Inject, Singleton}
import play.api.inject.ApplicationLifecycle
import play.api.Configuration

// This creates an `ApplicationStart` object once at start-up and registers hook for shut-down.
@Singleton
class ApplicationStart @Inject()(config: Configuration, lifecycle: ApplicationLifecycle) {

  // Shut-down hook
  lifecycle.addStopHook { () =>
    Future.successful {
        // Do tasks here on shutdown
    }
  }

  // Init tasks here
//  models.logging.Logging.init(config)
}

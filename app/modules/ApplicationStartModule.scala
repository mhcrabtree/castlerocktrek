package modules

import com.google.inject.AbstractModule

class ApplicationStartModule extends AbstractModule {
  override def configure() = {
    bind(classOf[ApplicationStart]).asEagerSingleton()
  }
}
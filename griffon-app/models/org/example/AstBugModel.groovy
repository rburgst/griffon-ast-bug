package org.example

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonModel)
@griffon.transform.Validateable
class AstBugModel {
    @FXObservable String clickCount = "0"
}

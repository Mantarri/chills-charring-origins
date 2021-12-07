package misteryman.chillscharringorigins.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import io.github.apace100.origins.origin.Origin;

public interface OriginComponent extends Component {
	String getOrigin();
	void setOrigin(Origin origin);
}

package bndtools.model.resolution;

import java.util.Map.Entry;

import org.bndtools.core.ui.icons.Icons;
import org.bndtools.core.ui.resource.R5LabelFormatter;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.osgi.resource.Capability;

public class CapabilityLabelProvider extends StyledCellLabelProvider {

	private final boolean shortenNamespaces;

	public CapabilityLabelProvider() {
		this(false);
	}

	public CapabilityLabelProvider(boolean shortenNamespaces) {
		this.shortenNamespaces = shortenNamespaces;
	}

	@Override
	public void update(ViewerCell cell) {
		Capability cap = (Capability) cell.getElement();

		StyledString label = new StyledString();
		R5LabelFormatter.appendCapability(label, cap, shortenNamespaces);
		cell.setText(label.toString());
		cell.setStyleRanges(label.getStyleRanges());

		// Get the icon from the capability namespace
		Image icon = Icons.image(R5LabelFormatter.getNamespaceImagePath(cap.getNamespace()), false);
		cell.setImage(icon);
	}

	@Override
	public String getToolTipText(Object element) {
		if (element instanceof Capability) {
			Capability cap = (Capability) element;

			StringBuilder buf = new StringBuilder();
			buf.append(cap.getNamespace());
			for (Entry<String, Object> attribute : cap.getAttributes()
				.entrySet())
				buf.append(";\n\t")
					.append(attribute.getKey())
					.append(" = ")
					.append(attribute.getValue());

			for (Entry<String, String> directive : cap.getDirectives()
				.entrySet())
				buf.append(";\n\t")
					.append(directive.getKey())
					.append(" := ")
					.append(directive.getValue());

			return buf.toString();
		}

		return null;
	}

}

package gg.vape.ui.click.component.value;

import gg.vape.Vape;
import gg.vape.ui.click.frame.impl.profile.PublicProfilesFrameHeaderActionComponent;
import gg.vape.ui.unmap.SearchBlock;

public class SearchBlockListDropdownLayer extends FloatingValueDropdownLayer<SearchBlockListComponent> {
   public SearchBlockListDropdownLayer(SearchBlockListComponent searchBlockListComponent) {
      super(searchBlockListComponent);
      this.Y(
         new PublicProfilesFrameHeaderActionComponent(this, "allowedicon", searchBlockListComponent.getTitle())
            .Q(new SearchBlockListDropdownCloseHandler(this, searchBlockListComponent))
      );
      this.refreshContents();
   }

   @Override
   public void refreshContents() {
      this.removeMarkedChildren();
      SearchBlockListAddInputComponent searchBlockListAddInputComponent = new SearchBlockListAddInputComponent("Block name / ID");
      this.h(searchBlockListAddInputComponent);

      for (SearchBlock searchBlock : Vape.INSTANCE.getSearch().getSearchBlocks()) {
         SearchBlockEditorComponent searchBlockEditorComponent = new SearchBlockEditorComponent(searchBlock);
         searchBlockEditorComponent.setRemoveClickListener(new SearchBlockRemoveHandler(this, searchBlock));
         this.h(searchBlockEditorComponent);
      }
   }
}

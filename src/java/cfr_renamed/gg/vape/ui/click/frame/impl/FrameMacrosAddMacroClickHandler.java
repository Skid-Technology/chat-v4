/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosAddMacroInputComponent;
import gg.vape.ui.click.frame.impl.FrameMacrosEditor;

class FrameMacrosAddMacroClickHandler
implements GuiClickListener {
    final FrameMacrosAddMacroInputComponent var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P;
    final FrameMacros var_gg_vape_ui_click_frame_impl_FrameMacros_D;

    @Override
    public void onPrimaryClick() {
        if (FrameMacrosAddMacroInputComponent.p$src$Z$xrofzd(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P)) {
            this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P.submit();
            if (FrameMacrosAddMacroInputComponent.j(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P) != null) {
                Macro macro = Macro.create(FrameMacrosAddMacroInputComponent.j(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P));
                if (Vape.INSTANCE.getMacrosManager().getMacro(FrameMacrosAddMacroInputComponent.j(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P)) != null) {
                    this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P.setText("");
                    ClientSettings.activeComponent = null;
                    return;
                }
                ClientSettings.activeComponent = null;
                FrameMacrosAddMacroInputComponent.w(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P, new FrameMacrosEditor(this.var_gg_vape_ui_click_frame_impl_FrameMacros_D, macro));
                FrameMacrosAddMacroInputComponent.p(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P).N$src$V$13y6z98();
                this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P.addChildren(FrameMacrosAddMacroInputComponent.p(this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P));
            }
            this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P.setText("");
        }
    }

    FrameMacrosAddMacroClickHandler(FrameMacrosAddMacroInputComponent frameMacrosAddMacroInputComponent, FrameMacros frameMacros) {
        this.var_gg_vape_ui_click_frame_impl_FrameMacrosAddMacroInputComponent_P = frameMacrosAddMacroInputComponent;
        this.var_gg_vape_ui_click_frame_impl_FrameMacros_D = frameMacros;
    }
}


# Vape client — zkm-flowdeobf control-flow deobfuscation

Date: 2026-10-16. Tool: `./zkm-flowdeobf` (cloned from
https://github.com/OpenVapeCN/zkm-flowdeobf, main class
`cn.openvape.flowdeobf.ControlFlowDeobfuscator`).
Untouched: `src/`, `ui/`, `payload.bin`, `payload_jar/vape_client.jar`,
`payload_jar/vape_inner.bin`, `payload_jar/classes/`.

## 2026-10-17 — refresh (Vape 4.22)

`scripts/update-payload.sh` fetched a newer payload, so the full-flags pass was
re-run against it:

| artifact | size | sha256 |
|---|---|---|
| `vape_client.jar` (Vape 4.22) | 25,096,879 | `75411df446b7af9ab963ddb864345c1a8124830fe102812986dcad6198d9b722` |
| `vape_client_deobf_full.jar` | 25,253,236 | `a6756f968093eb63c62f1482ba508b204d871b2aff1e2b49ea5293b129965ecd` |

Tool stdout: `classes=7604 changed=141 rounds=4 failures=0 mode=safe
identity_rethrows=removed exception_ranges=normalized zkm_shared_tails=normalized
residual_stacks=materialized binary_sentinel_tails=specialized
reference_sentinel_tails=specialized constant_residual_islands=cleaned`.

The input jar held 8,206 entries; the scanner still reports 7,604 classes.
Decompiling it produced 5,353 `.java` files. What is vendored as the buildable
payload is Vape's own code — `src/payload/java/gg/vape/**` (2,954 files) plus
`func/` — with the relocated `gg/vape/shaded` left out, because ASM and
Javassist now come from Maven and are re-relocated by the shade plugin (see
`docs/BUILD.md`).

The decompiler is **Vineflower 1.11.2**, not CFR: on this jar CFR 0.152 emits
114 syntax errors (`'finally' without 'try'`, `illegal start of expression`,
`$Gson$Types`…), Vineflower 6 — and those 6 are one pattern, rewritten by hand.

Everything below documents the original 2026-10-16 pass and stays accurate for
that capture.

## Exact commands run

```sh
# 1. build (from repo root)
cd zkm-flowdeobf && /opt/homebrew/bin/gradle compileJava --console=plain --no-daemon

# 2a. default (fail-closed safe) pass
/opt/homebrew/bin/gradle run --console=plain --no-daemon --args="\
/Volumes/SSD/scratch/skids/v4-chat/payload_jar/vape_client.jar \
/Volumes/SSD/scratch/skids/v4-chat/payload_jar/vape_client_deobf.jar \
/Volumes/SSD/scratch/skids/v4-chat/payload_jar/deobf_report.tsv"

# 2b. full-flags pass (README variant)
/opt/homebrew/bin/gradle run --console=plain --no-daemon --args="\
/Volumes/SSD/scratch/skids/v4-chat/payload_jar/vape_client.jar \
/Volumes/SSD/scratch/skids/v4-chat/payload_jar/vape_client_deobf_full.jar \
/Volumes/SSD/scratch/skids/v4-chat/payload_jar/deobf_report_full.tsv \
--remove-identity-rethrows --normalize-exception-ranges \
--normalize-zkm-shared-tails --materialize-residual-stacks \
--specialize-binary-sentinel-tails --clean-constant-residual-islands"
```

`--assume-zkm-sentinels` was deliberately NOT used (explicit-analysis mode only;
not part of the fail-closed default).

## Toolchain notes

- `java -version` → Temurin 21.0.10 (default JVM, also the Gradle launcher JVM).
- Gradle 9.5.1 (`/opt/homebrew/bin/gradle`) — README asks for Gradle 8.x, but
  **9.5.1 worked with zero build-script changes** (`BUILD SUCCESSFUL`, no
  verbatim errors to report).
- `java.toolchain languageVersion 17` resolved automatically: Temurin 17
  (`temurin-17.jdk`) is installed, so **no `org.gradle.java.installations.paths`
  override or `build.gradle` edit was needed**.
- One pre-restart `compileJava` invocation was cancelled by a server restart;
  the retry after restart succeeded in ~6 s (ASM 9.7.1 resolved from Maven
  Central; network available). No other failures.

## Before / after stats

| artifact | size | sha256 |
|---|---|---|
| `vape_client.jar` (original, untouched) | 24,424,167 | `1aece75437c65c0e1d8955966edfaad0769f8c77ddf5fe5e37794c43ee378318` |
| `vape_client_deobf.jar` (default pass) | 24,559,566 | `b733faf5a9f5e24671a113c4381d30707c8431468369f20af1f144cd6ef1202e` |
| `vape_client_deobf_full.jar` (full flags) | 24,580,473 | `c4ee40a5059774ea80c35aaabe4cff8608dd1297aff612f6e4a84eef3aec7892` |

Tool stdout summaries:

- Default: `classes=7604 changed=123 rounds=4 failures=0 mode=safe
  identity_rethrows=preserved exception_ranges=preserved
  zkm_shared_tails=preserved residual_stacks=preserved
  binary_sentinel_tails=preserved reference_sentinel_tails=preserved
  constant_residual_islands=preserved` (23 s)
- Full: `classes=7604 changed=143 rounds=4 failures=0 mode=safe
  identity_rethrows=removed exception_ranges=normalized
  zkm_shared_tails=normalized residual_stacks=materialized
  binary_sentinel_tails=specialized reference_sentinel_tails=specialized
  constant_residual_islands=cleaned` (32 s)

Entry counts: 8204 entries / 7604 classes before and after in both outputs
(3596 `gg/*` classes). Entry-name lists (classes and non-class resources)
are byte-identical between input and both outputs — non-class resources
preserved, no classes added/removed.

Report action counts (`deobf_report.tsv`, 2352 data rows):

| count | action |
|---|---|
| 1357 | remove-unreachable |
| 432 | zkm-path-skip-unproven (conservative skip, no rewrite) |
| 145 | remove-goto |
| 131 | coalesce-labels |
| 102 | zkm-path-pin-taken-guard |
| 102 | fold-constant |
| 52 | zkm-path-remove-false-guard |
| 15 | remove-invalid-local-variable-metadata |
| 12 | remove-empty-exception-ranges |
| 4 | fixed-point-round |

1208 rows touch `gg/vape/*`. Top classes by row count:
`gg/vape/render/OffscreenRenderContext` (908),
`CrystalAuraPlacementSubModule` (47), `RenderBatchManager` (42),
`TutorialManager` (18).

`deobf_report_full.tsv` (3036 data rows) adds exactly one new action,
`materialize-residual-stack` (402), plus knock-on `remove-unreachable`
1357→1615 and `coalesce-labels` 131→159. The other opt-in stages
(identity-rethrow removal, exception-range normalization, shared-tail
normalization, binary-sentinel specialization, residual-island cleaning)
ran but found **zero** provable sites — no corresponding rows, i.e. a
verified no-op on this input, not a failure.

## Sample javap diff

`javap -p` signatures of `gg.vape.Vape` are identical before/after
(only constant-pool index renumbering in `-c` output).

Real rewrite — `gg.vape.input.InputEventDispatcher.registerHandlers()V`
(before 319 → after 314 `javap -c` lines; identical again under full flags):

```diff
-      365: aload_1
-      366: ifnull        376
-      369: iconst_5
-      370: anewarray     #161  // class gg/vape/ui/click/component/GuiComponent
-      373: invokestatic  #165  // Method gg/vape/ui/click/component/GuiComponent.setLegacyComponentState:([Lgg/vape/ui/click/component/GuiComponent;)V
-      376: return
+      365: return
```

A provably-null guard was folded (`fold-constant … null constant
condition=true` at offset 200) and the 6 dead instructions on the impossible
path removed (`remove-unreachable` at 203–205). Pre/post `javap -c` dumps for
`Vape` and `InputEventDispatcher` were staged under `/tmp/deobf_work/`
(ephemeral; not part of the repo).

## Failures

None, beyond the pre-restart cancelled compile noted above. Both passes exit
0 with `failures=0`; the original jar's sha256 is unchanged.

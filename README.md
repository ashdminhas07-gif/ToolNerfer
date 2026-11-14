# ToolNerfer

Paper plugin for Minecraft 1.21.11.

## Features
- Mace hits against players are capped at **6 hearts (12 health points)**.
- Spear hits against players are capped at **6 hearts (12 health points)**.
- The cap applies regardless of the victim's armor, Protection enchantments, or the attacker's Speed effect.
- Hits that would deal less than 6 hearts are not increased.

## Build
Requires Java 21 and Maven:

```bash
mvn clean package
```

The plugin JAR is created in `target/`.

## GitHub Actions
Every push to `main` builds the plugin. Download the JAR from the workflow run's **Artifacts** section.

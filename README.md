# ItemSwapper - Origins Inventory

This is an addon made to create compatibility between Origins' Inventory power (as seen on the Shulk origin) and ItemSwapper.

This should also work with any other mod that utilises Apoli's power system.

![](https://i.imgur.com/y2cYA7r.gif)

## License
This project is licensed under the GPL-3 license, just like the original ItemSwapper mod. Please keep this in mind when utilising code or when making a fork of this mod.

## Implementing into your project
**build.gradle**
```groovy
repositories {
    maven {
        name = "Ladysnake Libs"
        url = 'https://ladysnake.jfrog.io/artifactory/mods'
    }
    maven {
        name = "JitPack"
        url = 'https://jitpack.io'
    }
    maven {
        url = 'https://maven.cafeteria.dev'
        content {
            includeGroup 'net.adriantodt.fabricmc'
        }
    }
    maven {
        url "https://maven.shedaniel.me/"
    }
    maven {
        url "https://maven.terraformersmc.com/"
    }
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "net.merchantpug:itemswapperorigins:${project.iso_version}"
}
```

**gradle.properties**
```properties
iso_version=[INSERT VERSION HERE]
```
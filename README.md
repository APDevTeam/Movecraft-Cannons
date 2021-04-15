# Movecraft-Cannons Integration
![Cannons](https://github.com/TylerS1066/Movecraft-Cannons/actions/workflows/maven.yml/badge.svg)

Home of the code for the following features:
 - Cannons plugin integration
 - Optional Movecraft-Combat integration (requires v1.2+ and Cannons v2.4.6+)

## Version support
The `legacy` branch is coded for 1.10.2 to 1.16.5 and Movecraft 7.x.

The `main` branch is coded for 1.14.4 to 1.16.5 and Movecraft 8.x.

## Download
Devevlopment builds can be found on the [GitHub Actions tab](https://github.com/TylerS1066/Movecraft-Cannons/actions) of this repository.

Stable builds can be found on [our SpigotMC page](https://www.spigotmc.org/resources/movecraft-cannons.86908/).

## Building
This plugin requires that the user setup and build their [Movecraft](https://github.com/APDevTeam/Movecraft) and [Movecraft-Combat](https://github.com/TylerS1066/Movecraft-Combat) development environments, and then clone this into the same folder as your Movecraft development environment such that Movecraft-Cannons, Movecraft-Combat and Movecraft are contained in the same folder.
This plugin also requires you to build the latest version of 1.13.2 using build tools.

```
java -jar BuildTools.jar --rev 1.14.4
```

Then, run the following to build Movecraft-Cannons through `maven`.
```
mvn clean install
```
Jars are located in `/target`.


## Support
[Github Issues](https://github.com/TylerS1066/Movecraft-Cannons/issuess)

[Discord](http://bit.ly/JoinAP-Dev)

The plugin is released here under the GNU General Public License V3. 

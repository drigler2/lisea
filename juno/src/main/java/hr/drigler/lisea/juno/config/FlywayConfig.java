/***************************************************************************
Copyright (C) 2020 Damir Rigler

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
***************************************************************************/
package hr.drigler.lisea.juno.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@DependsOn("dataSourceConfig")
public class FlywayConfig {

    DataSource dataSource;

    @Autowired
    public FlywayConfig(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public Flyway getFlyway() {

        FluentConfiguration config = new FluentConfiguration();
        config.dataSource(dataSource);

        Flyway flyway = new Flyway(config);

        flyway.migrate();

        return flyway;
    }
}

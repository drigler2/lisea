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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@DependsOn("jdbcTemplate")
public class DatabaseInitialiser {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseInitialiser.class);

    DataSource dataSource;

    @Value("${spring.datasource.populator.filename}")
    private String filename;

    @Value("${spring.datasource.populator.enabled}")
    private Boolean enabled;

    @Autowired
    public DatabaseInitialiser(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitialiser() {

        if (enabled) {
            LOG.debug("Attempting to initialise DB");

            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource(filename));

            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(dataSource);
            dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

            return dataSourceInitializer;
        }
        else {
            LOG.debug("Populating database disabled! Skipping....");
            return null;
        }
    }

}

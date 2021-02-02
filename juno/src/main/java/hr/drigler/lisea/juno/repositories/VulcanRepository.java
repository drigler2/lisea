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
package hr.drigler.lisea.juno.repositories;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import hr.drigler.lisea.juno.models.IVulcanId;

@Component
public class VulcanRepository implements IVulcanRepository {

    @Override
    public IVulcanId getVulcanId() {

        RestTemplate template = new RestTemplate();

        // TODO move to mercury
        return template.getForEntity("http://localhost:8180", VulcanId.class).getBody();
    }

    public static class VulcanId implements IVulcanId {

        Long vulcanId;

        @Override
        @JsonProperty(value = "snowflakedId")
        public Long getVulcanId() {

            return vulcanId;
        }
    }
}

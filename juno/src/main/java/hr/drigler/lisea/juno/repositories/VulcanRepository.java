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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import hr.drigler.lisea.juno.config.VulcanConfig.VulcanWrapper;
import hr.drigler.lisea.juno.models.IVulcanId;

@Component
public class VulcanRepository implements IVulcanRepository {

    private final VulcanWrapper vulcan;

    @Autowired
    public VulcanRepository(VulcanWrapper vulcan) {

        this.vulcan = vulcan;
    }

    @Override
    public IVulcanId getVulcanId() {

        return vulcan.getVulcanEndpoint().getForEntity("", VulcanId.class).getBody();
    }

    public static class VulcanId implements IVulcanId {

        Long vulcanId;

        // TODO workaround compile time const
        @Override
        @JsonProperty(value = "snowflakedId")
        public Long getVulcanId() {

            return vulcanId;
        }
    }
}

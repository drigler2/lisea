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
package hr.drigler.lisea.juno.test.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hr.drigler.lisea.juno.models.IVulcanId;
import hr.drigler.lisea.juno.repositories.IVulcanRepository;
import hr.drigler.lisea.juno.repositories.VulcanRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VulcanRepository.class)
@TestPropertySource(locations = "classpath:application.yml")
//@SpringBootTest
public class VulcanRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(VulcanRepository.class);

    IVulcanRepository repo;

    @Autowired
    public VulcanRepositoryTest(IVulcanRepository repo) {

        this.repo = repo;
    }

    @Test
    public void testGetxVulcanId() {

        LOG.debug("Fetching vulcan id....");
        IVulcanId r = repo.getVulcanId();
        LOG.debug("vulcan ID = {}", r.getVulcanId());
    }

}
